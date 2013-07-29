<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr" dir="ltr">
<head>
	<title>Source</title>

	<style>

		.c { color: #999; }  /* Comment */
		.o { color: #555555 } /* Operator */
		.k { color: #006699; } /* Keyword */
		.n { color: #CC00FF } /* Name */
		.m  { color: #FF6600 } /* Literal Number */
		.s  { color: #d44950 } /* Literal String */

<%--
		.err { color: #AA0000; background-color: #FFAAAA } /* Error */

		.c { color: #999; } /* Comment */
		.cm { color: #0099FF; font-style: italic } /* Comment.Multiline */
		.cp { color: #009999 } /* Comment.Preproc */
		.c1 { color: #999; } /* Comment.Single */
		.cs { color: #999; } /* Comment.Special */

		.o { color: #555555 } /* Operator */
		.ow { color: #000000; } /* Operator.Word */

		.gd { background-color: #FFCCCC; border: 1px solid #CC0000 } /* Generic.Deleted */
		.ge { font-style: italic } /* Generic.Emph */
		.gr { color: #FF0000 } /* Generic.Error */
		.gh { color: #003300; } /* Generic.Heading */
		.gi { background-color: #CCFFCC; border: 1px solid #00CC00 } /* Generic.Inserted */
		.go { color: #AAAAAA } /* Generic.Output */
		.gp { color: #000099; } /* Generic.Prompt */
		.gs { } /* Generic.Strong */
		.gu { color: #003300; } /* Generic.Subheading */
		.gt { color: #99CC66 } /* Generic.Traceback */

		.w { color: #bbbbbb } /* Text.Whitespace */

		.k { color: #006699; } /* Keyword */
		.kc { color: #006699; } /* Keyword.Constant */
		.kd { color: #006699; } /* Keyword.Declaration */
		.kn { color: #006699; } /* Keyword.Namespace */
		.kp { color: #006699 } /* Keyword.Pseudo */
		.kr { color: #006699; } /* Keyword.Reserved */
		.kt { color: #007788; } /* Keyword.Type */

		.na { color: #4f9fcf } /* Name.Attribute */
		.nb { color: #336666 } /* Name.Builtin */
		.nc { color: #00AA88; } /* Name.Class */
		.no { color: #336600 } /* Name.Constant */
		.nd { color: #9999FF } /* Name.Decorator */
		.ni { color: #999999; } /* Name.Entity */
		.ne { color: #CC0000; } /* Name.Exception */
		.nf { color: #CC00FF } /* Name.Function */
		.nl { color: #9999FF } /* Name.Label */
		.nn { color: #00CCFF; } /* Name.Namespace */
		.nt { color: #2f6f9f; } /* Name.Tag */
		.nv { color: #003333 } /* Name.Variable */
		.bp { color: #336666 } /* Name.Builtin.Pseudo */
		.vc { color: #003333 } /* Name.Variable.Class */
		.vg { color: #003333 } /* Name.Variable.Global */
		.vi { color: #003333 } /* Name.Variable.Instance */


		.m  { color: #FF6600 } /* Literal.Number */
		.mf { color: #FF6600 } /* Literal.Number.Float */
		.mh { color: #FF6600 } /* Literal.Number.Hex */
		.mi { color: #FF6600 } /* Literal.Number.Integer */
		.mo { color: #FF6600 } /* Literal.Number.Oct */
		.il { color: #FF6600 } /* Literal.Number.Integer.Long */
		.s  { color: #d44950 } /* Literal.String */
		.sb { color: #CC3300 } /* Literal.String.Backtick */
		.sc { color: #CC3300 } /* Literal.String.Char */
		.s2 { color: #CC3300 } /* Literal.String.Double */
		.se { color: #CC3300 } /* Literal.String.Escape */
		.sh { color: #CC3300 } /* Literal.String.Heredoc */
		.si { color: #AA0000 } /* Literal.String.Interpol */
		.sx { color: #CC3300 } /* Literal.String.Other */
		.sr { color: #33AAAA } /* Literal.String.Regex */
		.s1 { color: #CC3300 } /* Literal.String.Single */
		.ss { color: #FFCC33 } /* Literal.String.Symbol */
		.sd { color: #CC3300; font-style: italic } /* Literal.String.Doc */
--%>
	</style>
</head>
<body>

	<div style="font: 12px Monospace;">
		<%= request.getAttribute("source") %>
	</body>
</body>
</html>