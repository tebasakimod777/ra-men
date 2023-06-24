<?php
define("GETPRESS", "/usr/bin/sudo /home/pi/get_press/get_press");
define("ADDRESS", "127.0.0.1");
define("USER", "pi");
define("PASSWORD", "raspberry");
header("Access-Control-Allow-Origin: *");

$sconnection = ssh2_connect(ADDRESS, 22);
ssh2_auth_password($sconnection, USER, PASSWORD);

$command = GETPRESS;
$stdio_stream = ssh2_exec($sconnection, $command);

stream_set_blocking($stdio_stream, true);
$stream_out = ssh2_fetch_stream($stdio_stream, SSH2_STREAM_STDIO);


$pressure = stream_get_contents($stream_out);

fclose($stream_out);
fclose($stdio_stream);
ssh2_disconnect($sconnection);

$array = [
    "pressure" => [$pressure]
];


$json_str = json_encode($array);
echo $json_str;
file_put_contents("pressure.json", $json_str);
?>