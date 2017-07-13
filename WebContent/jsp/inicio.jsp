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
				${titulo}
			</p>
			<p id="vista_previa" class="right" style="font-size:14px;">
				Seleccione archivos para convertir...
			</p>
		</div>
	</div>
</div>
<div class="hr">
</div>
<!-- SUBHEADER -->

<!-- CONTENT -->
<form id="frmConvert" name="frmConvert" action="" method="post" enctype="multipart/form-data">
<div class="row">
	<div class="six columns" align="center">
		<input id="archivos" name="archivos" type="file" class="alert button" style="width:90%;" accept='application/pdf,text/plain' multiple onchange="openFile(event)"/><br><br>
		<table id="tabla" cellpadding="5px" border="1" width="90%"></table>
		<img id="pdf-icon" src="${pageContext.servletContext.contextPath}/images/pdf1.png" alt="icon" width="50px"/>
	</div>
	<div class="six columns" align="center">
		<input id="convert" type="button" class="success button" value="Convertir" onclick="convertir()" /><br><br>
		<img id="xls-icon" src="${pageContext.servletContext.contextPath}/images/pdfxls2.png" alt="icon" width="50px"/>
	</div>
</div>
</form>
<!-- CONTENT -->

<!-- RESULT -->
<div class="tweetarea">
	<div class="tweettext">
		<div class="row">
			<div class="twelve columns">
				<div><p>${convertidos}</p></div>
			</div>
		</div>
	</div>
</div>
<!-- RESULT -->

<%@include file="comun/footer.jsp"%>