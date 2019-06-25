package Polygon.greinerHorman;

import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.ListIterator;

public class GreinerHormanIterator implements ListIterator<Double> {

	private ListIterator<Double> it;
	private List<Double> l;

	public GreinerHormanIterator(List<Double> l,int index) {
		this.l = l;
		it = l.listIterator(index);
	}

	@Override
	public void add(Double arg0) {
		it.add(arg0);
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return it.hasPrevious();
	}

	@Override
	public Double next() {
		if (!it.hasNext())
			it = l.listIterator();
		return it.next();
	}

	@Override
	public int nextIndex() {
		if (it.nextIndex() == l.size())
			return 0;
		else
			return it.nextIndex();
	}

	@Override
	public Double previous() {
		if (!it.hasPrevious())
			it = l.listIterator(l.size());
		return it.previous();
	}

	@Override
	public int previousIndex() {
		if (it.previousIndex() == -1)
			return l.size() - 1;
		else
			return it.previousIndex();
	}

	@Override
	public void remove() {
		it.remove();
	}

	@Override
	public void set(Double arg0) {
		it.set(arg0);
	}

}
