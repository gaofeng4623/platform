package com.gtzn.modules.workflow.domain;

import com.gtzn.modules.workflow.domain.*;

import java.util.Comparator;


public class SortWFCommentByTime  implements Comparator<com.gtzn.modules.workflow.domain.WFComment> {

	@Override
	public int compare(com.gtzn.modules.workflow.domain.WFComment c1, com.gtzn.modules.workflow.domain.WFComment c2) {
		return c2.getTime().compareTo(c1.getTime());
	}

}
