const {createProxyMiddleware} = require('http-proxy-middleware');

module.exports = function (app) {
    app.use(
        ['/library/**', '/authenticate'],
        createProxyMiddleware({
            target: 'http://localhost:8099',
            secure: false
        })
    );
};