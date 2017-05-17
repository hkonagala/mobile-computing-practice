<?php

$latitude = $_REQUEST["latitude"];
$longitude = $_REQUEST["longitude"];
$timestamp = $_REQUEST["timestamp"];
$id = $_REQUEST["id"];


#$latitude = 72.5;
#$longitude = 49.7;
#$timestamp = "2017-11-4 08:12:00";
#$id = "nilanb";

#connect to a backend database

#$con = mysql_connect("eclipse.umbc.edu","nilanb","nilanb123~");

$mysqli = new mysqli("localhost", "nilanb", "nilanb123~", "FriendFinder");

if($mysqli->connect_errno){

  echo "Could not connect to the database\n";
  echo "Error: ". $mysqli->connect_errno . "\n";
  exit;
}

$query = "SHOW TABLES IN 'FriendFinder' where 'Tables_in_tests' = '$id'";
$result = $mysqli->query($query);

if($result == false)
{
	$query = "CREATE TABLE $id(Date DATETIME PRIMARY KEY, 
		  Latitude DECIMAL(16,10),
		  Longitude DECIMAL(16,10))";

	$result = $mysqli->query($query);
	if(!$result)
	{
		print("The table was not created " . $mysqli->error . "\n");
	}
}

 $query = "INSERT INTO $id(Date, Latitude, Longitude) VALUES('$timestamp', 
 $latitude, $longitude)";
 $result = $mysqli->query($query);
  if(!$result)
	{
		print("Data was not inserted into the table " . $mysqli->error . "\n");
	}

 $listdbtables = $mysqli->query('SHOW TABLES');
 $idarray = array();
 $latitudearray = array();
 $longitudearray = array();
 $i = 0;


 while($row = $listdbtables->fetch_row())
 {
        $tb_name = $row[0];
	print("$tb_name \n");
	$query = "SELECT * from $tb_name  order by Date desc limit 1";
	$result = $mysqli->query($query);
	$idarray[] = $tb_name;

	while($row = $result->fetch_assoc()) {
	$fdate = $row['Date'];
	$flatitude = $row['Latitude'];
	$flongitude = $row['Longitude'];
	$longitudearray[] = $flongitude;
	$latitudearray[] = $flatitude;
	$i++;
  }
 }

 for ($k = 0; $k < $i; $k++)
  {
     print("$idarray[$k],");
     print("$latitudearray[$k],");
     print("$longitudearray[$k]");
     print("\n");
  }

$mysqli->close();

?>
