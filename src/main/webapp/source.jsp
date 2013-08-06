<%@ taglib prefix="h" uri="http://highlight.abachar.fr/highlight" %>

<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
<head>
	<title>Source</title>
	<link rel="stylesheet" href="assets/css/reset.css" />
	<link rel="stylesheet" href="assets/css/style.css" />
	<link rel="stylesheet" href="assets/css/theme-github.css" />
</head>
<body>

	<div class="code highlight">
		<%= request.getAttribute("source") %>
	</body>

</body>
</html>