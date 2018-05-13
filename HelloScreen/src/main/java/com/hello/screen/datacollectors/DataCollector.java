package com.hello.screen.datacollectors;

import java.util.List;

public interface DataCollector<T> {
	
	public List<T> collect();

}
