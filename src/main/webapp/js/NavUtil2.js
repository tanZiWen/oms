$(document).ready(function(){
	$('.tablelist tbody tr:even').addClass('odd');
});

var NavUtil = {
	modelId : "",
	totalNum : 50,
	PageNum : 1,
	PageSize : 0,
	totalPages : 0,
	_bindPageEvent:function(fun) {
		$("#"+NavUtil.modelId+" a").live("click", function() {
			var clickNo = $(this).text();
			if(clickNo == '<'){
				if(NavUtil.PageNum == 1){
					return false;
				}else{
					clickNo = NavUtil.PageNum-1;
				}
			}else if(clickNo == '>'){
				if(NavUtil.totalPages == NavUtil.PageNum || NavUtil.totalPages == 0){
					return false;
				}else{
					clickNo = NavUtil.PageNum+1;
				}
			}
			NavUtil.PageNum = clickNo;
			//NavUtil.setPage(NavUtil.modelId,NavUtil.totalPages,parseInt(clickNo));
			fun();
			return false;
		});
	},
	bindPageEvent : function(fun) {
		$("#"+NavUtil.modelId+" a").on("click", function() {
			var clickNo = $(this).text();
			if(clickNo == '<'){
				if(NavUtil.PageNum == 1){
					return false;
				}else{
					clickNo = NavUtil.PageNum-1;
				}
			}else if(clickNo == '>'){
				if(NavUtil.totalPages == NavUtil.PageNum || NavUtil.totalPages == 0){
					return false;
				}else{
					clickNo = NavUtil.PageNum+1;
				}
			}
			NavUtil.PageNum = clickNo;
			//NavUtil.setPage(NavUtil.modelId,NavUtil.totalPages,parseInt(clickNo));
			fun();
			return false;
		});
	},
	setPage : function(modelId, totalNum, PageNum) {
		NavUtil.modelId = modelId;
		NavUtil.totalNum = totalNum;
		var t = parseInt(NavUtil.totalNum / NavUtil.PageSize);
		NavUtil.totalPages = NavUtil.totalNum % NavUtil.PageSize == 0
				? t
				: t + 1;
		NavUtil.PageNum = PageNum;
		var a = [];
		if (PageNum == 1) {
			a[a.length] = "<a href=\"#\" class=\"prev unclick\"><</a>";
		} else {
			a[a.length] = "<a href=\"#\" class=\"prev\"><</a>";
		}
		function setPageList(i) {
			if (PageNum == i) {
				a[a.length] = "<a href=\"#\" class=\"on\">" + i + "</a>";
			} else {
				a[a.length] = "<a href=\"#\">" + i + "</a>";
			}
		}
		// 总页数小于10
		if (NavUtil.totalPages <= 10) {
			for (var i = 1; i <= NavUtil.totalPages; i++) {
				setPageList(i);
			}
		}
		// 总页数大于10页
		else {
			if (PageNum <= 4) {
				for (var i = 1; i <= 5; i++) {
					setPageList(i);
				}
				a[a.length] = "<span class=\"ignore\">...</span><a href=\"#\">" + NavUtil.totalPages + "</a>";
			} else if (PageNum >= NavUtil.totalPages - 3) {
				a[a.length] = "<a href=\"#\">1</a><span class=\"ignore\">...</span>";
				for (var i = NavUtil.totalPages - 4; i <= NavUtil.totalPages; i++) {
					setPageList(i);
				}
			} else { // 当前页在中间部分
				a[a.length] = "<a href=\"#\">1</a><span class=\"ignore\">...</span>";
				for (var i = PageNum - 2; i <= PageNum + 2; i++) {
					setPageList(i);
				}
				a[a.length] = "<span class=\"ignore\">...</span><a href=\"#\">" + NavUtil.totalPages + "</a>";
			}
		}
		if (PageNum == NavUtil.totalPages) {
			a[a.length] = "<a href=\"#\" class=\"next unclick\">></a>";
		} else {
			a[a.length] = "<a href=\"#\" class=\"next\">></a>";
		}
		$("#"+NavUtil.modelId).html(a.join(""));
	}
};
