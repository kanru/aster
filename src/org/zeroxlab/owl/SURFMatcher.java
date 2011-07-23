/*
 * SURFMatcher.java
 */

package org.zeroxlab.owl;

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;

import static java.lang.Math.abs;

public class SURFMatcher implements IMatcher {
    @Override
    public MatchResult find(IplImage haystack, IplImage needle) {
        CvSize rsize = cvSize(abs(haystack.width() - needle.width()) + 1,
                              abs(haystack.height() - needle.height()) + 1);

        IplImage result = cvCreateImage(rsize, IPL_DEPTH_32F, 1);
        cvMatchTemplate(haystack, needle, result, CV_TM_SQDIFF_NORMED);

        double[] min = new double[1];
        double[] max = new double[1];
        CvPoint min_pos = new CvPoint();
        CvPoint max_pos = new CvPoint();
        cvMinMaxLoc(result, min, max, min_pos, max_pos, null);

        return new MatchResult(min_pos.x(), min_pos.y(),
                               needle.width(), needle.height(), 1.0 - min[0]);
    }
}