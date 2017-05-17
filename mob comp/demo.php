#php web service:

$latitude = $_REQUEST["latitude"];
$longitude = $_REQUEST["longitude"];
$timestamp = $_REQUEST["timestamp"];
$id = $_REQUEST["id"];

#connect to a backend database

$con = mysql_connect("eclipse.umbc.edu","harika","somepwd"); #server name, username, pwd
if(!$con)
{
	die("did not connect to the mysql server:". mysql_error());
}

$result = mysql_select_db("friendFinder", $con);
if(!$result)
{
	die("did not select the database:" . mysql_error());
}

if(!tabel_exists($id))
{
	$query = "CREATE TABLE $id(Date DATETIME PRIMARY KEY, Latitude DECIMAL(10,16),
				Longitude DECIMAL(10,16))";
	$result = mysql_query($query);
	if(!$result)
	{
		die("the table was not created" . mysql_error());
	}
}
