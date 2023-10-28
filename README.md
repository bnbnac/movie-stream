# Local video streaming web app
로컬에 저장된 비디오를 스트리밍하는 웹서비스입니다. nginx를 이용해
 mp4 비디오와 vtt 자막을 제공하면 특별한 설정 없이도 크롬, 브레이브,
 사파리 등의 클라이언트 브라우저가 잘 연동되어 partial-content를
 받아 재생합니다. 추후 데이터베이스를 연동하여 remote 서버에서
 비디오를 제공할 수 있도록 업데이트 할 예정입니다.

---
### need three system variables
- $MOVIE_STREAM : password to login(static on server. user just input this)
- $MOVIE_SERVER : `protocol`://`router-ip`:`data-listening-port`/storage
- $MOVIE_STORAGE : root data directory. the structure must be like below
  <br><br>
root
<br>
|--영화1
<br>
|&nbsp;&nbsp;&nbsp;├video.mp4
<br>
|&nbsp;&nbsp;&nbsp;├sub.vtt
<br>
|&nbsp;&nbsp;&nbsp;ㄴthumbnail.webp
<br>
|
<br>
|--영화2
<br>
...

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