package Test;

class Test {
	public static void main(String[] args) {
		Queue q = new Queue();
		Produce p = new Produce(q);
		Consumer c = new Consumer(q);
		p.start();
		c.start();
	}
}

// 生产者线程类
class Produce extends Thread {
	Queue q;

	Produce(Queue q) {
		this.q = q;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			q.put(i);
			System.out.println("Product i " + i);
		}
	}
}

// 消费者线程类
class Consumer extends Thread {
	Queue q;

	Consumer(Queue q) {
		this.q = q;
	}

	public void run() {
		while (true) {
			System.out.println("Consumer get" + q.get());
		}
	}
}

class Queue {
	int value;
	// 判断队列是否是满的，如果是满的说明数据没有被消费者获取
	boolean bFull = false;
		public synchronized void put(int i) {
				if (!bFull) {
					value = i;
					bFull = true;
					notify();
				}
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

//就消费者而言
	public synchronized int get() {
		//如果队列有值则返回
		if(!bFull)
		{
			try {
				//消费者需要等待
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		bFull = false;
		//通知生产者继续放置数据
		notify();
		return value;
	}
}
