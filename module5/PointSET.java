/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> points;

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> ret = new Stack<>();
        points.forEach(point -> {
            if (point.x() > rect.xmin() && point.x() < rect.xmax() && point.y() > rect.ymin()
                    && point.y() < rect.ymax()) {
                ret.push(point);
            }
        });
        return ret;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET ps = new PointSET();
        ps.insert(new Point2D(1, 2));
        ps.insert(new Point2D(7, 5));
        ps.insert(new Point2D(2, 4));
        ps.insert(new Point2D(6, 1));
        ps.insert(new Point2D(9, 2));
        ps.draw();
        Iterable<Point2D> stack = ps.range(new RectHV(0, 1, 3, 5));
        for (Point2D p : stack) {
            System.out.println(p);
        }


    }
}
