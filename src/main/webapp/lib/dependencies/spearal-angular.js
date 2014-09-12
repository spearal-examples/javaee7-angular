function spearalAngularConfigureResources(resourceProvider, spearalFactory) {
	
	var headers = {
		"Accept": "application/spearal",
		"Content-Type": "application/spearal"
	};
	
	function encode(data, headersGetter) {
		if (data) {
			var encoder = spearalFactory.newEncoder();
			encoder.writeAny(data);
			return new Uint8Array(encoder.buffer);
		}
	}

	function decode(arraybuffer, headersGetter) {
		if (arraybuffer && arraybuffer.byteLength > 0) {
			var decoder = spearalFactory.newDecoder(arraybuffer);
			return decoder.readAny();
		}
	}

	var actions = resourceProvider.defaults.actions;
	for (method in actions) {
		var action = actions[method];
		action.responseType = 'arraybuffer';
		action.headers = headers;
		action.isArray = false;
		action.transformRequest = [ encode ];
		action.transformResponse = [ decode ];
	}
}

