package com.olvynchuru.thepeoplemap.thepeoplemap;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.support.v4.graphics.PathParser;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nomo on 10/04/2017.
 */

public class ZoomableVectorView extends View {

    private static final String TAG_VECTOR = "vector";
    private static final String TAG_PATH = "path";
    private static final String TAG = "VECTOR_SHAPE";

    private RectF viewportRect = new RectF();
    private List<Layer> layers = new ArrayList<>();

    private HashMap<String, Integer> colorMap;

    private Context context;

    //These two constants specify the minimum and maximum zoom

    private static float MIN_ZOOM = 1f;
    private static float MAX_ZOOM = 5f;



    private float scaleFactor = 1.f;
    private ScaleGestureDetector detector;



    //These constants specify the mode that we're in

    private static int NONE = 0;
    private static int DRAG = 1;
    private static int ZOOM = 2;



    private int mode;



    //These two variables keep track of the X and Y coordinate of the finger when it first
    //touches the screen

    private float startX = 0f;
    private float startY = 0f;


    //These two variables keep track of the amount we need to translate the canvas along the X
    //and the Y coordinate

    private float translateX = 0f;
    private float translateY = 0f;



    //These two variables keep track of the amount we translated the X and Y coordinates, the last time we
    //panned.

    private float previousTranslateX = 0f;

    private float previousTranslateY = 0f;

    private boolean dragged = false;
    private float displayWidth;
    private float displayHeight;


    public ZoomableVectorView(Context context) {
        super(context);
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());
    }


    public ZoomableVectorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }

    public ZoomableVectorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        detector = new ScaleGestureDetector(getContext(), new ScaleListener());

    }



    //set and parse data from vector drawable resource
    public void setVectorResource(Context context, int resId) {
        this.context = context;
        this.colorMap = new HashMap<>();
        XmlResourceParser parser = context.getResources().getXml(resId);

        AttributeSet set = Xml.asAttributeSet(parser);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {

                if(eventType == XmlPullParser.START_TAG) {

                    String tagName = parser.getName();

                    if (tagName.equals(TAG_VECTOR)) {

                        int[] attrs = { android.R.attr.viewportWidth, android.R.attr.viewportHeight };
                        TypedArray ta = context.obtainStyledAttributes(set, attrs);
                        viewportRect.set(0, 0, ta.getFloat(0, 0), ta.getFloat(0, 0));
                        ta.recycle();

                    } else if (tagName.equals(TAG_PATH)) {

                        int[] attrs = {android.R.attr.name, android.R.attr.pathData };
                        TypedArray ta = context.obtainStyledAttributes(set, attrs);
                        layers.add(new Layer(ta.getString(0), ta.getString(0)));
                        ta.recycle();

                    }
                }

                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRegionAt(int x, int y) {
//        outLayers.clear();
        String regionName = null;
        Log.d(TAG, "finding layers at: " + x + ", " + y);
        for (Layer layer : layers) {
            if (layer.region.contains(x, y)) {
                Log.d(TAG, "Layer Name: " + layer.name);

                if(!layer.name.equals(context.getResources().getString(R.string.region_name_background)))
                    regionName = layer.name;
            }
        }

        return regionName;
    }

    public void setRegionColor(String regionName, int colorResourceId){

        colorMap.put(regionName, context.getResources().getColor(colorResourceId));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:
                mode = DRAG;

                //We assign the current X and Y coordinate of the finger to startX and startY minus the previously translated
                //amount for each coordinates This works even when we are translating the first time because the initial
                //values for these two variables is zero.

                startX = event.getX() - previousTranslateX;
                startY = event.getY() - previousTranslateY;
                break;

            case MotionEvent.ACTION_MOVE:
                translateX = event.getX() - startX;
                translateY = event.getY() - startY;

                //We cannot use startX and startY directly because we have adjusted their values using the previous translation values.
                //This is why we need to add those values to startX and startY so that we can get the actual coordinates of the finger.

                double distance = Math.sqrt(Math.pow(event.getX() - (startX + previousTranslateX), 2) +
                        Math.pow(event.getY() - (startY + previousTranslateY), 2)
                );


                if(distance > 0) {
                    dragged = true;
                }

                break;



            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                break;



            case MotionEvent.ACTION_UP:
                mode = NONE;
                dragged = false;

                //All fingers went up, so let's save the value of translateX and translateY into previousTranslateX and
                //previousTranslate

                previousTranslateX = translateX;
                previousTranslateY = translateY;
                break;



            case MotionEvent.ACTION_POINTER_UP:
                mode = DRAG;


                //This is not strictly necessary; we save the value of translateX and translateY into previousTranslateX
                //and previousTranslateY when the second finger goes up
                previousTranslateX = translateX;
                previousTranslateY = translateY;

                break;

        }



        detector.onTouchEvent(event);


        //We redraw the canvas only in the following cases:
        //
        // o The mode is ZOOM
        //        OR
        // o The mode is DRAG and the scale factor is not equal to 1 (meaning we have zoomed) and dragged is
        //   set to true (meaning the finger has actually moved)

        if ((mode == DRAG && scaleFactor != 1f && dragged) || mode == ZOOM) {
            invalidate();
        }



        return true;

    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        //We're going to scale the X and Y coordinates by the same amount
        canvas.scale(this.scaleFactor, this.scaleFactor, this.detector.getFocusX(), this.detector.getFocusY());

        //If translateX times -1 is lesser than zero, let's set it to zero. This takes care of the left bound

        if((translateX * -1) < 0) {
            translateX = 0;
        }



        //This is where we take care of the right bound. We compare translateX times -1 to (scaleFactor - 1) * displayWidth.
        //If translateX is greater than that value, then we know that we've gone over the bound. So we set the value of
        //translateX to (1 - scaleFactor) times the display width. Notice that the terms are interchanged; it's the same
        //as doing -1 * (scaleFactor - 1) * displayWidth

        else if((translateX * -1) > (scaleFactor - 1) * displayWidth) {
            translateX = (1 - scaleFactor) * displayWidth;
        }

        if(translateY * -1 < 0) {
            translateY = 0;
        }

        //We do the exact same thing for the bottom bound, except in this case we use the height of the display
        else if((translateY * -1) > (scaleFactor - 1) * displayHeight) {
            translateY = (1 - scaleFactor) * displayHeight;
        }


        //We need to divide by the scale factor here, otherwise we end up with excessive panning based on our zoom level
        //because the translation amount also gets scaled according to how much we've zoomed into the canvas.
        canvas.translate(translateX / scaleFactor, translateY / scaleFactor);


        /* The rest of your canvas-drawing code */
        drawCanvas(canvas);

        canvas.restore();

    }

    //draw canvas for vector drawable
    private void drawCanvas(Canvas canvas) {

        if(layers == null)
            return;

        Paint paint = new Paint();

        for (Layer layer : layers) {

            if(layer.name.equals(context.getResources().getString(R.string.region_name_background))) {
                paint.setColor(context.getResources().getColor(R.color.color_background));

                Paint strokePaint = new Paint(paint);
                strokePaint.setStyle(Paint.Style.STROKE);
                strokePaint.setStrokeWidth(2);
                strokePaint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
                canvas.drawPath(layer.transformedPath, paint);
                canvas.drawPath(layer.transformedPath, strokePaint);

            }else{
                Integer mapColor = colorMap.get(layer.name);
                if(mapColor != null){
                    paint.setColor(mapColor);
                }else {
                    paint.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }

                canvas.drawPath(layer.transformedPath, paint);
            }


        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(layers == null)
            return;

        //update transforms for layers
        Matrix matrix = new Matrix();
        Region shapeRegion = new Region(0, 0, w, h);
        matrix.setRectToRect(viewportRect, new RectF(0, 0, w, h), Matrix.ScaleToFit.CENTER);
        for (Layer layer : layers) {
            layer.transform(matrix, shapeRegion);
        }

        displayWidth = w;
        displayHeight = h;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            scaleFactor *= detector.getScaleFactor();

            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM));

            return true;

        }

    }


    class Layer {
        Path originalPath;
        Path transformedPath = new Path();
        Region region = new Region();
        String name;

        @SuppressLint("RestrictedApi")
        public Layer(String data, String name) {
            originalPath =  PathParser.createPathFromPathData(data);
            this.name = name;
        }

        public void transform(Matrix matrix, Region clip) {
            originalPath.transform(matrix, transformedPath);
            region.setPath(transformedPath, clip);
        }

        @Override public String toString() { return name; }
    }

}

