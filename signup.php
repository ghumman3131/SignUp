<?php
// reading data sent from app in form of jsonobject
$data = file_get_contents('php://input');

// decoding data mean extracting data from jsonobject
$data_decoded = json_decode($data , true);

// getting each value from decoded data by providing key which is given in app while making json object
$name = $data_decoded['name_key'];
$mobile = $data_decoded['mobile_key'];
$email = $data_decoded['email_key'];
$password = $data_decoded['password_key'];

// making connection with database and save it in connection variable
$connection = mysqli_connect('localhost' , 'root' , '');


// selecting database
mysqli_select_db($connection , 'user');

// query to check if that email which user is using for signup if already exist or not
$result = mysqli_query($connection , "select email from user_details where email = '$email'");

// if in above query some rows are returned it mean email exist so we will check for number of rows returned by foolowing function
$rows_found = mysqli_num_rows($result);

// if number of rows == 0 it mean that email do not exist in our database so user can sign up with that email
if($rows_found == 0){
	// query to insert user details in database
mysqli_query($connection, "insert into user_details (name , email , mobile , password) 

values ('$name' , '$email' , '$mobile' , '$password') " );

	// if data is inserted we will create response by making key value pair
	// here key name is 'key' and value is 1
        $response['key'] = "1";
	// converting key value pair into json object and echo it
	echo json_encode($response);
}

// else part will run if number rows returned are more then 0 that mean email already exist 
else {
	// here we generate same key name 'key' as above but with different value 0
	$response['key'] = "0";
	// converting key value pair into json object and echo it
	echo json_encode($response);
}


?>
