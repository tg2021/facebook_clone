CREATE TABLE t_user(
   iuser INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
   email VARCHAR(50) UNIQUE NOT NULL,
   pw VARCHAR(100) NOT NULL,
   nm VARCHAR(5) NOT NULL,
   tel CHAR(13) COMMENT '연락처',
   authCd CHAR(5) COMMENT '회원가입 인증코드, null이면 인증받은 상태, 값이 있으면 인증해야 되는 상태',
   mainProfile VARCHAR(50),
   regdt DATETIME DEFAULT NOW(),
   INDEX idx_auth_cd (authCd)
);

CREATE TABLE t_user_profile(
    iprofile INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    iuser INT UNSIGNED,
    img VARCHAR(50),
    regdt DATETIME DEFAULT NOW(),
    FOREIGN KEY(iuser) REFERENCES t_user(iuser)
);


CREATE TABLE t_feed(
    ifeed INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    location VARCHAR(20),
    ctnt TEXT,
    iuser INT UNSIGNED NOT NULL,
    regdt DATETIME DEFAULT NOW(),
    FOREIGN KEY (iuser) REFERENCES t_user(iuser)
);


CREATE TABLE t_feed_img(
    ifeedimg INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    ifeed    INT UNSIGNED NOT NULL,
    img      VARCHAR(50) NOT NULL,
    FOREIGN KEY (ifeed) REFERENCES t_feed (ifeed)
);

SELECT A.ifeed, A.location, A.ctnt,A.iuser, A.regdt,
       B.ifeedimg, B.img,
       C.nm AS writer, C.mainProfile
FROM t_feed A
LEFT JOIN t_feed_img B
ON A.ifeed = B.ifeed
INNER JOIN t_user C
ON A.iuser = C.iuser
ORDER BY A.ifeed DESC;

CREATE TABLE t_feed_fav (
  ifeed INT UNSIGNED,
  iuser INT UNSIGNED,
  regdt DATETIME DEFAULT NOW(),
  PRIMARY KEY (ifeed, iuser),
  FOREIGN KEY(ifeed) REFERENCES t_feed (ifeed),
  FOREIGN KEY(iuser) REFERENCES t_user (iuser)
);