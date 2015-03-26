<!DOCTYPE html>
<?php
	require_once('database.inc.php');
	
	session_start();
	$db = $_SESSION['db'];
	$db->openConnection();
	$pallets = $db->getPallets();
	$db->closeConnection();
?>

<html lang="en"><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://getbootstrap.com/favicon.ico">

    <title>Krusty Kookies production</title>

    <!-- Bootstrap core CSS -->
    <link href="Carousel%20Template%20for%20Bootstrap_files/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="Carousel%20Template%20for%20Bootstrap_files/starter-template.css" rel="stylesheet">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="Carousel%20Template%20for%20Bootstrap_files/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="Index.php">Krusty Cookies</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="Index.php">Home</a></li>
            <li><a href="Production.php">Production</a></li>
            <li class="active"><a href="Searchandblock.php">Searching and blocking</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container">

      <div class="starter-template">
       <h1>Search and Block</h1>
<form  method=post action="block.php">
		Select pallet to block:
		<br>
		<select class="form-control" name="pallet" size=10>
		<?php
			$first = true;
			foreach ($pallets as $row) {
				
				if ($first) {
					print "<option selected>";
					$first = false;
				} else {
					print "<option>";
				}
				print $row['PalletID'] . " " . $row['Location'];
			
		}
		?>
		</select>
    <input class="btn btn-default" type="submit" value="Block">
</form>
<br>
<form method=post action="search.php">
 Search for pallet with ID:
	<br>
    <input class="form-control" type="text" size="20" name="pallet" >
    <input class="btn btn-default" type="submit" value="Search">
</form>
      </div>

    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="Carousel%20Template%20for%20Bootstrap_files/jquery.js"></script>
    <script src="Carousel%20Template%20for%20Bootstrap_files/bootstrap.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="Carousel%20Template%20for%20Bootstrap_files/ie10-viewport-bug-workaround.js"></script>
  

</body></html>
