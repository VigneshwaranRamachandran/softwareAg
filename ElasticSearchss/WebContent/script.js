var app = angular.module('mainApp', []);
app.controller('ctrl', function($scope) {
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
		$.ajax({
		type: 'GET',
		 contentType: 'text/plain',
		data: url,
      url: 'http://localhost:7080/ElasticSearchs/index/get?urls='+url,						
      success: function(data) {
    	  document.getElementById("response").innerHTML=data;
      } });
	}
$scope.sendpost=function(postForm){
	var jsonl=document.getElementById("json").value;
		var url=document.getElementById("url").value;
		document.getElementById("url").value="";
		$("#json").remove();
		
		var requestData = { jsonl : jsonl, 
				url : url
               }
		
		
		//alert(jsonl);
		$.ajax({
		type: 'POST',
		contentType: "application/json",
		data:JSON.stringify(requestData),
     // url: 'http://localhost:7080/ElasticSearchs/index/post?urls='+url,
      url: 'http://localhost:7080/ElasticSearchs/index/post',
      success: function(data) {
    	  alert(data);
    	 // document.getElementById("response").innerHTML=data;
      } });
		 document.getElementById("response").innerHTML=requestData.jsonl;
	}
$scope.bodyreq=function(postForm){
	
	document.getElementById("body").innerHTML='<textarea rows="15" cols="100" id="json"></textarea>';
	
}
});