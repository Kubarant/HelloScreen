package com.hello.screen;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonUtil {

	public static ArrayList<JsonNode> mapToArraylist(JsonNode listNode) {
		ArrayList<JsonNode> result = new ArrayList<>();
		for (int i = 0; i < listNode.size(); i++) {
			result.add(listNode.get(i));
		}
		return result;
		
	}
	
}
