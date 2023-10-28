# Local video streaming web app

---
### need three system variables
- $MOVIE_STREAM : password to login(static on server. user just input this)
- $MOVIE_STORAGE : data directory
- $MOVIE_SERVER : ${protocol}://${router-ip}:${data-serving-port-forworded-by-router}/storage

---

### nginx config example
```nginx
http {
	types {
		text/vtt vtt;
	}

	server {
		listen ${hosting-port-forworded-by-router};
		location / {
			proxy_pass http://127.0.0.1:{port-where-spring-boot-is-running}/;
		}
	}

	server {
		listen ${data-serving-port-forworded-by-router};

		location /storage/ {
			auth_request /auth;
			alias ${data-directory}/;
			error_page 401 = ${protocol}://${router-ip}:${hosting-port-forworded-by-router}/;
			add_header 'Access-Control-Allow-Origin' '${protocol}://${router-ip}:${hosting-port-forworded-by-router}/';
			add_header 'Access-Control-Allow-Credentials' 'true';
			add_header 'Access-Control-Allow-Headers' 'Origin, Content-Type, Accept, Authorization';
		}

		location /auth {
			internal;
			proxy_pass http://127.0.0.1:${port-where-spring-boot-is-running}/auth;
		}
	}

	sendfile on;
	tcp_nopush on;
	types_hash_max_size 2048;
        server_tokens off;

	include /etc/nginx/mime.types;
	default_type application/octet-stream;

	ssl_protocols TLSv1 TLSv1.1 TLSv1.2 TLSv1.3; # Dropping SSLv3, ref: POODLE
	ssl_prefer_server_ciphers on;

	access_log /var/log/nginx/access.log;
	error_log /var/log/nginx/error.log;

	gzip on;
	
	include /etc/nginx/conf.d/*.conf;
	include /etc/nginx/sites-enabled/*;
}
```