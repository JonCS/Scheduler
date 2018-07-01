<?php

include "connect.php";

	$getsql = "SELECT * FROM projecttable";
	$result = $conn->query($getsql);

	$sortsql = "SELECT * FROM projecttable
	ORDER BY date_1 DESC";
	$result = $conn->query($sortsql);

	if ($result->num_rows > 0) {
		echo "<table style='border: 1px solid black;' cellspacing=0 cellpadding=15>";
		while ($row = $result -> fetch_assoc()){
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
$conn->close();
?>