package us.zeropen.zroid.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

import us.zeropen.zroid.game.scene.ZScene;
import us.zeropen.zroid.graphic.ZObject;

/**
 * Created by Dohyung on 2015-05-24.
 *
 * parentScene을 멤버로 가져서 add, remove, clear 등의 메서드를 사용하면
 * parentScene가 자동으로 addChild를 호출하는 ArrayList입니다.
 * 단, 자료형은 ZObject나 그 하위 클래스만을 사용할 수 있습니다.
 * 만약 생성자에서 parentScene을 null로 설정하면 addChild를 호출하지 않습니다.
 *
 */
public class ZArrayList<E extends ZObject> extends ArrayList<E> {
	ZScene parentScene;

	public ZArrayList(Collection<? extends E> collection, ZScene parentScene) {
		super(collection);
		this.parentScene = parentScene;
	}

	public ZArrayList(int capacity, ZScene parentScene) {
		super(capacity);
		this.parentScene = parentScene;
	}

	public ZArrayList(ZScene parentScene) {
		super();
		this.parentScene = parentScene;
	}

	@Override
	public boolean add(E object) {
		if (parentScene != null)
			parentScene.addChild(object);
		return super.add(object);
	}

	@Override
	public void add(int index, E object) {
		if (parentScene != null)
			parentScene.addChild(object);
		super.add(index, object);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		if (parentScene != null)
			for (E object : collection) {
				parentScene.addChild(object);
			}
		return super.addAll(collection);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> collection) {
		if (parentScene != null)
			for (E object : collection) {
				parentScene.addChild(object);
			}
		return super.addAll(index, collection);
	}

	@Override
	public void clear() {
		if (parentScene != null)
			for (E object : this) {
				parentScene.removeChild(object);
				object.setRender(false);
				object.setUpdate(false);
			}
		super.clear();
	}

	@Override
	public E remove(int index) {
		if (parentScene != null) {
			E object = this.get(index);
			parentScene.removeChild(object);
			object.setRender(false);
			object.setUpdate(false);
		}
		return super.remove(index);
	}

	@Override
	public boolean remove(Object object) {
		if (parentScene != null && object instanceof ZObject) {
			parentScene.removeChild((ZObject) object);
			((ZObject) object).setRender(false);
			((ZObject) object).setUpdate(false);
		}
		return super.remove(object);
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		if (parentScene != null)
			for (int i = fromIndex; i <= toIndex; ++i) {
				parentScene.removeChild(this.get(i));
				this.get(i).setRender(false);
				this.get(i).setUpdate(false);
			}
		super.removeRange(fromIndex, toIndex);
	}

	public ZArrayList<E> randomSelect(int n) {
		ZArrayList<E> ret = new ZArrayList<>(this.parentScene);
		HashSet<Integer> randIndex = new HashSet<>();
		Random r = new Random();
		while (randIndex.size() < n) {
			randIndex.add(new Integer(r.nextInt(this.size())));
		}
		for (int i : randIndex) {
			ret.add(this.get(i));
		}
		return ret;
	}
}
