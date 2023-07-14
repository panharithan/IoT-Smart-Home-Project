<?php 
session_start();
if (isset($_SESSION['user_id'])) { 
  header('location: /IOT_Project/index.php');
}

$message = '';
if (
  isset($_POST['name']) &&
  isset($_POST['password']) 
)
{
  $name = $_POST['name'];
  $password = $_POST['password'];
  $con = new PDO('mysql:host=localhost;dbname=auth', 'admin', 'password');
  $statement = $con->prepare('select * from users where name=:name');
  $statement->execute([
    ':name' => $name
  ]);
  $user = $statement->fetch(PDO::FETCH_OBJ);
  if (!$user) {
    $message = "Your username not found in database. You didn't register yet";
  } else {
    if (password_verify($password, $user->password)) {
      $_SESSION['user_id'] = $user->id;
      header('location: /IOT_Project/index.php');
    }else {
      $message = "Your have put wrong password";
    }
  }

}
 ?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Login page</title>
  <link rel="stylesheet" href="bootstrap.min.css">
</head>
<body>
  <div class="container mt-5">
    <div class="card">
      <div class="card-header">
        <h2>Login</h2>
      </div>
      <div class="card-body">
        <?php if(!empty($message)): ?>
          <div class="alert alert-danger">
            <?= $message; ?>
          </div>
        <?php endif; ?>
        <form action="" method="post">
          <div class="form-group">
            <label for="name">Username</label>
            <input type="text" name="name" id="name" class="form-control" required>
          </div>

          <div class="form-group">
            <label for="password">Password</label>
            <input type="password" name="password" id="password" class="form-control" required>
          </div>

          <div class="form-group">
            <button class="btn btn-outline-primary" type="submit">Login Now</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>