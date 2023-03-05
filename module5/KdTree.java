/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private int size;
    private Node root;

    private class Node {
        private Point2D point;
        private RectHV rectangle;
        private Node left;
        private Node right;

        private Node(Point2D point, RectHV rectangle) {
            this.point = point;
            this.rectangle = rectangle;
            left = null;
            right = null;
        }
    }

    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point) {
        // construct an empty set of points
        double xmin = 0.0;
        double ymin = 0.0;
        double xmax = 1.0;
        double ymax = 1.0;
        root = insert(root, point, 0, xmin, ymin, xmax, ymax);
    }

    private Node insert(Node node, Point2D point, int level, double xmin, double ymin, double xmax,
                        double ymax) {
        if (node == null) {
            size++;
            return new Node(point, new RectHV(xmin, ymin, xmax, ymax));
        }
        int compare = compare(point, node.point, level);
        if (compare < 0) {
            node.left = (level % 2 == 0 ?
                         insert(node.left, point, level + 1, xmin, ymin, node.point.x(), ymax) :
                         insert(node.left, point, level + 1, xmin, ymin, xmax, node.point.y()));
        }
        else {
            node.right = (level % 2 == 0 ?
                          insert(node.right, point, level + 1, node.point.x(), ymin, xmax, ymax) :
                          insert(node.right, point, level + 1, xmin, node.point.y(), xmax, ymax));

        }
        return node;
    }

    private int compare(Point2D p1, Point2D p2, int level) {
        if (level % 2 == 0) {
            return Double.compare(p1.x(), p2.x()) == 0 ?
                   Double.compare(p1.y(), p2.y()) :
                   Double.compare(p1.x(), p2.x());
        }
        return Double.compare(p1.y(), p2.y()) == 0 ?
               Double.compare(p1.x(), p2.x()) :
               Double.compare(p1.y(), p2.y());
    }

    // does the set contain point p?
    public boolean contains(Point2D point) {
        return contains(root, point, 0) != null;
    }

    private Point2D contains(Node node, Point2D point, int level) {
        if (node != null) {
            int compare = compare(point, node.point, level);
            if (compare < 0) {
                return contains(node.left, point, level + 1);
            }
            else if (compare > 0) {
                return contains(node.right, point, level + 1);
            }
            else {
                return node.point;
            }
        }
        return null;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.clear();

    }

    private void drawLine(Node node, int level) {
        if (node != null) {
            drawLine(node.left, level + 1);
            StdDraw.setPenRadius();
            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(node.point.x(), node.rectangle.ymin(), node.point.x(),
                             node.rectangle.ymax());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(node.rectangle.xmin(), node.point.y(), node.rectangle.xmax(),
                             node.point.y());
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.point.draw();
            drawLine(node.right, level + 1);
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> queue = new Queue<Point2D>();

        addRange(root, rect, queue);

        return queue;
    }

    private void addRange(Node node, RectHV rectangle, Queue<Point2D> queue) {
        if (node != null && rectangle.intersects(node.rectangle)) {
            if (rectangle.contains(node.point)) {
                queue.enqueue(node.point);
            }

            addRange(node.left, rectangle, queue);
            addRange(node.right, rectangle, queue);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D point) {
        if (isEmpty()) {
            return null;
        }
        else {
            Point2D nearest = nearest(root, point, null);
            return nearest;
        }
    }

    private Point2D nearest(Node node, Point2D point, Point2D min) {
        if (node != null) {
            if (min == null) {
                min = node.point;
            }
            if (min.distanceSquaredTo(point) >= node.rectangle.distanceSquaredTo(point)) {
                if (node.point.distanceSquaredTo(point) < min.distanceSquaredTo(point)) {
                    min = node.point;
                }

                if (node.right != null && node.right.rectangle.contains(point)) {
                    min = nearest(node.right, point, min);
                    min = nearest(node.left, point, min);
                }
                else {
                    min = nearest(node.left, point, min);
                    min = nearest(node.right, point, min);
                }
            }
        }
        return min;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
