<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Github Account Stats</h2>
<form method="post" action="saveDetails">
	Enter Github Account Username: <input type="text" name="username"/>
	<input type="text" id="hiddenField" name="hiddenField" value="checked" style="display:none"/>
<br>

<input type="submit" value="Submit">

<br>

<input type="checkbox" id="forked" onchange="unforked()" name="forked1" value="forked" checked>
<label for="vehicle1"> Include forked repositories</label>

<script>
function unforked(){
	if(document.getElementById("hiddenField").value==="checked")
	{
		document.getElementById("hiddenField").value="unchecked";
	}
	else 
	{
		document.getElementById("hiddenField").value="checked";
	}
}
</script>

</body>
</html>