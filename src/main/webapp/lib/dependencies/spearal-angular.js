function spearalAngularConfigureResources($resourceProvider, spearalFactory) {

	console.log("[Spearal] Configuring Angular resource provider");
	
	if (!spearalFactory)
		spearalFactory = new SpearalFactory();
	
	function encode(data, headersGetter) {
		var array = null;
		if (data) {
			console.log("[Spearal] Encoding data: " + data);
			var encoder = spearalFactory.newEncoder();
			encoder.writeAny(data);
			array = new Uint8Array(encoder.buffer);
		}
		return array;
	}

	function decode(arraybuffer, headersGetter) {
		var result = null;
		if (arraybuffer && arraybuffer.byteLength > 0) {
			console.log("[Spearal] Decoding array buffer: " + arraybuffer.byteLength + " bytes");
			var decoder = spearalFactory.newDecoder(arraybuffer);
			result = decoder.readAny();
		}
		return result;
	}
	
	var headers = {
		"Accept": "application/spearal",
		"Content-Type": "application/spearal"
	};

	var actions = $resourceProvider.defaults.actions;

	for (method in actions) {
		var action = actions[method];
		
		action.responseType = 'arraybuffer';
		action.headers = headers;
		action.isArray = false;
		action.transformRequest = [ encode ];
		action.transformResponse = [ decode ];
	}
}

