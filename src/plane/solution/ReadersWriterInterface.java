package plane.solution;

import java.util.Date;

public interface ReadersWriterInterface {
	public  void writer(String flight, int j, Date date) throws InterruptedException;

	public void reader(String s) throws InterruptedException;

	public void writer(String s, Integer val) throws InterruptedException;

}

