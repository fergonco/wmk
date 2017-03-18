define(["module"], function(module) {
	console.log(module.config());
	return module.config().text;
});