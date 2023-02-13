import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        if (hasNullPoint(points))
            throw new IllegalArgumentException("User provided a null-value point");
        if (hasRepeats(points)) // can't have repeats
            throw new IllegalArgumentException(
                    "User provided two points with the same coordinates");

        for (int i = 0; i < points.length; i++) {
            Point p = points[i]; // The point we're using as reference.
            Point[] pointsCopy = new Point[points.length - 1];
            for (int j = 0, k = 0; j < points.length;
                 j++) { // Copy all elements except p to not need comparing it to each point later on.
                if (points[j] != points[i]) {
                    pointsCopy[k++] = points[j];
                }
            }
            Arrays.sort(pointsCopy, p.slopeOrder()); // Sort by slope.
            for (int j = 0; j < pointsCopy.length;
                 j++) { // Go through all elements in our sorted list.
                if (j < pointsCopy.length
                        - 1) { // Be careful we haven't reached the last one.  If so, we can't compare to a non-existing next one.
                    Point currentPoint = pointsCopy[j]; // The current point we're checking.
                    Point nextPoint = pointsCopy[j + 1]; // The next point we want to compare to.
                    if (p.slopeTo(currentPoint) == p.slopeTo(
                            nextPoint)) { // If they have the same slope towards p.
                        ArrayList<Point> segmentPoints
                                = new ArrayList<>(); // Create a temporary segment point list.
                        segmentPoints.add(p); // Add the first point
                        segmentPoints.add(currentPoint); // Add the current point we've checked
                        while (j < pointsCopy.length - 1 && segmentPoints.size() <= 4
                                && p.slopeTo(currentPoint) == p.slopeTo(nextPoint)) {
                            segmentPoints.add(nextPoint);
                            j++;
                            if (j < pointsCopy.length - 1) {
                                currentPoint = pointsCopy[j];
                                nextPoint = pointsCopy[j + 1];
                            }
                        }
                        if (segmentPoints.size() >= 4
                                && segmentPoints.get(0) == segmentPoints.stream()
                                                                        .min(Point::compareTo)
                                                                        .get()) {
                            segments.add(new LineSegment(
                                    segmentPoints.stream().min(Point::compareTo).get(),
                                    segmentPoints.stream().max(Point::compareTo).get()));
                        }
                    }
                }

            }

        }
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

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
