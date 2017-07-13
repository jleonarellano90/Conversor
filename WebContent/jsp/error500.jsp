<%@include file="comun/header.jsp"%>

<script src="${pageContext.servletContext.contextPath}/js/sistema/inicio.js"></script>

<!-- HEADER -->
<div class="row">
	<div class="four columns">
		<div class="logo">
			<a href="inicio.do">
				<img src="${pageContext.servletContext.contextPath}/images/pdfxls1.png" width="60%">
			</a>
		</div>
	</div>
	<div class="eight columns noleftmarg">		
			<nav id="nav-wrap">
				<ul class="nav-bar sf-menu">
					<li class="current">
						<a href="inicio.do">Inicio</a>
					</li>
					<li>
						<a href="contacto.do">Contacto</a>
					</li>				
				</ul>
			</nav>
		</div>
</div>
<div class="clear">
</div>
<!-- HEADER -->

<!-- SUBHEADER -->
<div id="subheader">
	<div class="row">
		<div class="twelve columns" align="center">
			<p class="left">
			</p>
			<p id="vista_previa" class="right" style="font-size:14px;">
			</p>
		</div>
	</div>
</div>
<div class="hr">
</div>
<!-- SUBHEADER -->

<!-- CONTENT -->
<form id="frmConvert" name="frmConvert" action="" method="post">
<div class="row">
	<div class="twelve columns" align="center">
		<h1 style="font-size: 700%; color: #333; line-height: 1.5"> Error 500
			<span class="particle particle--c"></span>
			<span class="particle particle--a"></span>
			<span class="particle particle--b"></span>
		</h1>
		<div align="center">
			<img src="${pageContext.servletContext.contextPath}/images/redfox.png" alt="RedFox" height="100" width="180">
			<h2 style="color: #666; line-height: 1.5;"> Ha sucedido un error técnico. Lo sentimos mucho.</h2>
			<p> Por favor describanos el incidente en la sección de Contacto. </p>
		</div>
	</div>
</div>
</form>
<!-- CONTENT -->

<%@include file="comun/footer.jsp"%>