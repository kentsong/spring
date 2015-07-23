<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Spring MVC - Upload File</title>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
  <script src="http://malsup.github.com/jquery.form.js"></script>
  
<script type="text/javascript" >

//using jquery.form.js
function uploadJqueryForm(){
    $('#result').html('');

   $("#form2").ajaxForm({
	success:function(data) { 
	      //$('#result').text(data+" uploaded by Jquery Form plugin!");
	      $('#result').html(data);

	 },
	 dataType:"text"
   }).submit();
}
//---------------------------------------------------------
//using FormData() object

	function uploadFormData() {
		$('#result').html('');

		var oMyForm = new FormData();
		oMyForm.append("file1Name", file2.files[0]);

		$.ajax({
			url : 'upload',
			data : oMyForm,
			dataType : 'text',
			processData : false,
			contentType : false,
			type : 'POST',
			success : function(data) {
				//  $('#result').html(data+ " uploaded by FormData!");
				$('#result').html(data);

			}
		});
	}
</script>
</head>

<body>
<h1>SpringMVC - File Upload with/without Ajax</h1> 

<!--  Form 1 -->
<i>Uploading File Without Ajax</i><br/>
<form id="form1" method="post" action="upload" enctype="multipart/form-data">
  
  <!-- File input -->     
  <input type="file" name="file1Name" /> <input type="submit" value="上傳" />
 
</form>

<br/><br/>
<i>Uploading File With Ajax</i><br/>
<!--  Form 2 -->
<form id="form2" method="post" action="upload" enctype="multipart/form-data">
  <!-- File input -->     
  <input name="file1Name" id="file2" type="file" /><br/>
</form>

<button value="Submit" onclick="uploadJqueryForm()" >Upload</button><i>Using JQuery Form Plugin</i><br/>
<button value="Submit" onclick="uploadFormData()" >Upload</button><i>Using FormData Object</i>

<div id="result"></div>
</body>
</html>
