<?php

	include "connect.php";


	if (isset($_POST['submitted'])) {
		$query = $_POST["name_query"];
		$sqlinsert = "INSERT INTO var_storage (query_one) 
		VALUES ('$query');";
		$conn->multi_query($sqlinsert);
	}

	$latest_entry = "SELECT * FROM var_storage ORDER BY id DESC LIMIT 1";
	$result = $conn->query($latest_entry);

	$result_var =mysqli_fetch_assoc ($result);
	echo"<br>";
	echo"<br>";
	echo "Searching for: " . $result_var["query_one"];

	$sql_filter = "SELECT * FROM projecttable WHERE name_1 = '$result_var[query_one]' OR name_2 = '$result_var[query_one]' ORDER BY date_1 DESC";
	$result_2 = $conn->query($sql_filter); 
	var_dump($result_2);

if ($result_2->num_rows > 0) {
		echo "<table style='border: 1px solid black;' cellspacing=0 cellpadding=15>";
		while ($row = $result_2 -> fetch_assoc()){
			if($row["name_2"] != NULL){
				$var_name_2 = " and " . $row["name_2"];
			}else{
				$var_name_2 = " ";
			}
			echo "<tr>";
			echo "<td>" . "Date: " . $row["date_1"] . "</td>";
			echo "<td>" . "Assignment ". $row["assignment_number"] . "</td>";
			echo "<td>" . $row["assignment_name"] . "</td>";
			echo "<td>" . $row["name_1"] . $var_name_2 . "</td>";
			echo "<td>" . " Point " . $row["point_1"] . "</td>";
			echo "</tr>";
		}
		echo "</table>";
}



?>