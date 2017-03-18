define(["pluginB/MyModule", "utils/resources"], function(myModule, resources) {
	var html = resources["pluginA/MyModule"].replace("{{text}}", myModule);
	document.body.innerHTML = html;
});