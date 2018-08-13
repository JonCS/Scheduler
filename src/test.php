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
	<input type = "radio" name = "assignment_number" value = "Reading"> Reading <br>
	<input type = "radio" name = "assignment_number" value = "Initial Visit"> Initial Visit <br>
	<input type = "radio" name = "assignment_number" value = "RV1"> RV1 <br>
	<input type = "radio" name = "assignment_number" value = "RV2"> RV2 <br>
	<input type = "radio" name = "assignment_number" value = "RV3"> RV3 <br>
	<input type = "radio" name = "assignment_number" value = "Study"> Study <br>
	<input type = "radio" name = "assignment_number" value = "Talk"> Talk <br>
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

<?php

$list_names = "SELECT DISTINCT name_1 FROM projecttable";
$result_list = $conn->query($list_names);
$array_names = array();
if ($result_list->num_rows > 0) {
	while ($row = $result_list -> fetch_assoc()){
			array_push($array_names, $row["name_1"]);
		}
}
$x = 0;
$temp = array();
$name_and_date = array();
if (count($array_names) > 0){
	while ($x < count($array_names)){
		$most_recent = "SELECT date_1 FROM projecttable WHERE name_1 = '$array_names[$x]' ORDER BY date_1 DESC LIMIT 1";
		$result = $conn->query($most_recent);
		$result_var =mysqli_fetch_assoc ($result);
		$temp = array($array_names[$x], $result_var['date_1']);
		array_push($name_and_date, $temp);
		$x ++;
	}
}
//var_dump($name_and_date);

date_default_timezone_set('America/Los_Angeles');
$date = new DateTime(date('Y/m/d', time()));
//var_dump($date);

for ($y = 0; $y < count($name_and_date); $y++) {
	echo "<br>" . $name_and_date[$y][0];
	$temp_date = new DateTime ($name_and_date[$y][1]);
	$interval = $date->diff($temp_date);
	echo "<br>" . $interval->format('%R%a days');

}

?>


</body>
</html>
