package us.zeropen.zroid.util;

/**
 * Created by Dohyung on 2015-05-17.
 * 생성자 호출 시 {} 안에 onElapsed를 오버라이드해서 사용합니다
 */
public class ZTimer {
	protected float currentTime = 0, duration;
	protected float elapsingSpeed = 1.0f;
	protected boolean running;

	public ZTimer(float duration) {
		this.duration = duration;
		this.running = true;
	}
	public ZTimer(float duration, boolean running) {
		this.duration = duration;
		this.running = running;
	}

	public void onElapsed() {}

	public void update(float eTime) {
		if (running) {
			this.currentTime += eTime * elapsingSpeed;
			if (isElapsed()) {
				this.running = false;
				onElapsed();
			}
		}
	}

	public boolean isElapsed() {
		return currentTime >= duration;
	}

	public void reset(boolean running) {
		currentTime = 0;
		this.running = running;
	}

	public float getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(float currentTime) {
		this.currentTime = currentTime;
	}

	public void addCurrentTime(float currentTime) {
		this.currentTime += currentTime;
	}

	public float getDuration() {
		return duration;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	public float getElapsingSpeed() {
		return elapsingSpeed;
	}

	public void setElapsingSpeed(float elapsingSpeed) {
		this.elapsingSpeed = elapsingSpeed;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
