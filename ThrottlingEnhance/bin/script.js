var app = angular.module('mainApp', []);
app.controller('ctrl', function($scope) {
	var hitCount=1;
	$scope.ge=function(){
		return window.open("get.html", "_self");;
	}
	$scope.pos=function(){
		return window.open("post.html", "_self");;
	}
	$scope.dele=function(){
		return window.open("delete.html", "_self");;
	}
	$scope.sendget=function(getForm){
		
		var url=document.getElementById("url").value;
		document.getElementById("url").value="";
		// to check number of request 
		 if(typeof(Storage) !== "undefined") {
		        if (sessionStorage.clickcount) {
		            sessionStorage.clickcount = Number(sessionStorage.clickcount)+1;
		            hitCount=sessionStorage.clickcount;
		            $.ajax({
		        		type: 'GET',
		        		 contentType: 'text/plain',
		        		data: url,
		              url: 'http://localhost:7070/ThrottlingEnhance/index/get?urls='+url+'&count='+sessionStorage.clickcount,						
		              success: function(data) {
		            	  document.getElementById("response").innerHTML=data;
		              } });
		        } else {
		            sessionStorage.clickcount = 1;
		            $.ajax({
		        		type: 'GET',
		        		 contentType: 'text/plain',
		        		data: url,
		              url: 'http://localhost:7070/ThrottlingEnhance/index/get?urls='+url+'&count='+sessionStorage.clickcount,						
		              success: function(data) {
		            	  document.getElementById("response").innerHTML=data;
		              } });
		        }
		       
		    }
	}
$scope.sendpost=function(postForm){
	var jsonl=document.getElementById("json").value;
		var url=document.getElementById("url").value;
		document.getElementById("url").value="";
		$("#json").remove();
		if(typeof(Storage) !== "undefined") {
	        if (sessionStorage.clickcount1) {
	            sessionStorage.clickcount1 = Number(sessionStorage.clickcount1)+1;
	            hitCount1=sessionStorage.clickcount1;
	            var requestData = { jsonl : jsonl, 
	    				url : url,
	    				click : sessionStorage.clickcount1
	                   }
	            $.ajax({
	        		type: 'POST',
	        		contentType: "application/json",
	        		data:JSON.stringify(requestData),
	              url: 'http://localhost:7070/ThrottlingEnhance/index/post',
	              success: function(data) {
	            	 // alert(data);
	            	 document.getElementById("response").innerHTML=data;
	              } });
	        } else {
	            sessionStorage.clickcount1 = 1;
	            var requestData = { jsonl : jsonl, 
	    				url : url,
	    				click : sessionStorage.clickcount1
	                   }
	            $.ajax({
	        		type: 'POST',
	        		contentType: "application/json",
	        		data:JSON.stringify(requestData),
	              url: 'http://localhost:7070/ThrottlingEnhance/index/post',
	              success: function(data) {
	            	  //alert(data);
	            	  document.getElementById("response").innerHTML=data;
	              } });
	        }
	       
	    }
		// document.getElementById("response").innerHTML=requestData.jsonl;
	}
$scope.bodyreq=function(postForm){
	
	document.getElementById("body").innerHTML='<textarea rows="15" cols="100" id="json"></textarea>';
	
}
$scope.getApi=function(getApiForm){
	 var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
	 var string_length = 20;
	 var randomstring = '';
	 for (var i=0; i<string_length; i++) {
	  var rnum = Math.floor(Math.random() * chars.length);
	  randomstring += chars.substring(rnum,rnum+1);
	 }
	
	document.getElementById("key").innerHTML=randomstring;
	
}
$scope.setLimit=function(throtlingForm){
	var api=$("#api").val();
	var count=$("#limitCount").val();
	var min=$("#limitMin").val();
	var periods=document.getElementById("limitperiods").value;
	var requestData = { count : count, 
			min : min,
			periods : periods,
			api : api
           }
//alert(api);
	if(min<60){
	$.ajax({
		type: 'POST',
		 contentType: 'application/json',
		 data:JSON.stringify(requestData),
		 url:'http://localhost:7070/ThrottlingEnhance/index/throtling',						
      success: function(data) {
    	  alert(data);
    	  document.getElementById("response").value=data;
      }
		
	});
	}
	else{
		alert("aboue 59min is not allowed");
		document.getElementById("limitMin").value=1;
	}
}
});