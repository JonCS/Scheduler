<?php

include "connect.php";

if (isset($_POST['submitted'])) {
	print_r("inside if");
	$date_1 = $_POST['date_1'];
	$assignment_number = $_POST['assignment_number'];
	$assignment_name = $_POST['assignment_name'];
	$name_1 = $_POST['name_1'];
	$name_2 = $_POST['name_2'];
	$point_1 = $_POST['point_1']; 
	$sqlinsert = "INSERT INTO projecttable (date_1, assignment_number, assignment_name, name_1, name_2, point_1) 
	VALUES ('$date_1', '$assignment_number', '$assignment_name', '$name_1', '$name_2', '$point_1');";
	$conn->multi_query($sqlinsert);
}
?>



<!DOCTYPE html>
<html>
<head>
<title>Help plz</title>
</head>
<body>

<h1>Add assignment</h1>

<form method = "post" action = "test.php">
<input type="hidden" name = "submitted" value="true">
<fieldset>
	Date (YYYYMMDD) <br>
	<input type="text" name = "date_1"> <br>
	<br>
	Assignment type <br>
	<input type = "radio" name = "assignment_number" value = "1"> Reading <br>
	<input type = "radio" name = "assignment_number" value = "2"> 1 <br>
	<input type = "radio" name = "assignment_number" value = "3"> 2 <br>
	<input type = "radio" name = "assignment_number" value = "4"> 3 <br>
	<br>
	Assignment name <br>
	<input type= "text" name = "assignment_name"> <br>
	<br>
	Student <br>
	<input type= "text" name = "name_1"> <br>
	<br>
	Assistant <br>
	<input type= "text" name = "name_2"> <br>
	<br>
	Point <br>
	<input type=" text" name = "point_1"> <br>
	<br>
	<input type= "submit" value = "Add assignment">
</fieldset>
</form>

<form method = "post" action = "search_name.php" target = "_blank">
<input type="hidden" name = "submitted" value="true">
<fieldset>
	Search for name <br>
	<input type=" text" name = "name_query"> <br>
	<br>
	<input type= "submit" value = "Search">
</fieldset>
</form>


<a href="access.php" target="_blank">All assignments</a>

<br>
<br>

<?php

$list_names = "SELECT DISTINCT name_1, name_2 FROM projecttable";
$result_list = $conn->query($list_names);
$array_names = array();
echo "Students";
	if ($result_list->num_rows > 0) {
		echo "<table style='border: 1px solid black;' cellspacing=0 cellpadding=15>";
		echo "<tr>";
		while ($row = $result_list -> fetch_assoc()){
			echo "<td>" . " " . $row["name_1"] . "</td>";
			$arraynames[]= $row["name_1"];
			$arraycount = count($arraynames) ;
			$y = 0;
			for ($x = 0; $x < $arraycount; $x++){
				$arraycount = count($arraynames) ;
				if($arraynames[$x] != $row["name_2"]){
					$y++;
				}
				if($y == $arraycount && $row["name_2"] != NULL){
					echo "<td>" . " " . $row["name_2"] . "</td>";
				}
			}

		}
		echo "</tr>";
		echo "</table>";
}

?>


</body>
</html>