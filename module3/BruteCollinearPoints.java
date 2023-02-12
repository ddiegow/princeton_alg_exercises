/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        if (hasNullPoint(points))
            throw new IllegalArgumentException("User provided a null-value point");
        if (hasRepeats(points)) // can't have repeats
            throw new IllegalArgumentException(
                    "User provided two points with the same coordinates");
        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);
        for (int i = 0; i < pCopy.length - 3; i++) {
            for (int j = i + 1; j < pCopy.length - 2; j++) {
                double slopeIJ = pCopy[i].slopeTo(pCopy[j]);
                for (int k = j + 1; k < pCopy.length - 1; k++) {
                    double slopeIK = pCopy[i].slopeTo(pCopy[k]);
                    if (slopeIJ == slopeIK) {
                        for (int l = k + 1; l < pCopy.length; l++) {
                            double slopeIL = pCopy[i].slopeTo(pCopy[l]);
                            if (slopeIK == slopeIL) {
                                segments.add(new LineSegment(pCopy[i], pCopy[l]));
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasRepeats(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasNullPoint(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) { // Points cannot be null
                return true;
            }
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] result = new LineSegment[segments.size()];
        result = segments.toArray(result);
        return result;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        System.out.println(n);
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }

        StdDraw.show();
    }

}
