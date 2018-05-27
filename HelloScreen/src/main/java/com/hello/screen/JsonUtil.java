package com.hello.screen;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public class JsonUtil {

	public static ArrayList<JsonNode> mapToArraylist(JsonNode listNode) {
		ArrayList<JsonNode> result = new ArrayList<>();
		for (int i = 0; i < listNode.size(); i++) {
			result.add(listNode.get(i));
		}
		return result;
		
	}
	
}
