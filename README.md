# Football

## ๐ ์๋น์ค ์ค๋ช
์์ํ์ด ์๋๋ผ๋ ์ํ๋ ์ฅ์, ์๊ฐ์ ์ถ๊ตฌ๋ ํ์ด ๊ฒฝ๊ธฐ๋ฅผ ์งํํ  ์ ์๋๋ก ๊ฒฝ๊ธฐ๋ฅผ ๋งค์นญํด์ฃผ๋ ์๋น์ค์๋๋ค.

## ๐ ์ฌ์ฉ ๊ธฐ์  ์คํ
- Java / Spring Boot / Gradle
- JPA(์์ )
- MySQL / Redis
- Docker
- Elastic

## ๐ ERD(์์ฑ ์์ )

## ๐ Use Case
### User
- ์ฌ์ฉ์๋ ํ์๊ฐ์, ๋ก๊ทธ์ธ, ๋ก๊ทธ์์์ ํ  ์ ์์ต๋๋ค.
- ํ์๊ฐ์ ๋ฐ ๋ก๊ทธ์ธ์ ํ ํ์๋ง์ด ์ดํ ์๋น์ค๋ฅผ ์ด์ฉํ  ์ ์์ต๋๋ค.

### Match
- ์ฌ์ฉ์๋ ์ํ๋ ๋งค์น๋ฅผ ์ ํํ  ์ ์์ต๋๋ค.
    - ๋งค์น๋ ๋ ์ง, ์ฅ์, ์ฑ๋ณ, ๋์ด๋์ ๋ฐ๋ผ ๊ตฌ๋ถ๋ฉ๋๋ค.
    - ์ ํํ ๋งค์น์ ๋ํด์  **๋งค์นํฌ์ธํธ**, **์์ ํ๊ท  ๋ ๋ฒจ**, **๊ฒฝ๊ธฐ์ฅ ์ ๋ณด**, **๋งค์น ์๋ด**๋ฅผ ํ์ธํ  ์ ์์ต๋๋ค.
- ์ฌ์ฉ์๋ ์ ํํ ๋งค์น์ ์ฐธ์ฌ์ ์ฒญ์ ํ  ์ ์์ต๋๋ค.
- ์ฐธ์ฌ์ ์ฒญ์ ํฌ๋งํ  ๊ฒฝ์ฐ, ์บ์๋ฅผ ์ถฉ์ ํ๊ณ  ๊ฒฐ์ ๋ฅผ ์งํํฉ๋๋ค.

### Payment
- ๊ฒฐ์ ๋ ๋งค์น๋น ์ง๋ถ ๊ฐ๊ฒฉ์ ๋ํด ์บ์๋ก ์ง๋ถํฉ๋๋ค.
- ์บ์ ํ๊ธ ๊ฒฐ์ ๋ฅผ ํตํด ์ถฉ์ ํ  ์ ์์ต๋๋ค.
- ์ถฉ์ ๋ ์บ์๊ฐ ๋ถ์กฑํ๋ฉด ์๋ฆผ ๋ฉ์์ง์ ํจ๊ป ์บ์ ์ถฉ์  ํ์ด์ง๋ก ์ด๋ํฉ๋๋ค.

### Manager
- ๋งค๋์ ๋ ๊ณ ์  ๋งค๋์ ์ ์ผ์ผ ๋งค๋์ ๋ก ๊ตฌ๋ถํฉ๋๋ค.
    - ๊ณ ์  ๋งค๋์ ๋ ํน์  ๋งค์น์ ๋ํด ์ ๋ด์ผ๋ก ๊ด๋ฆฌ, ๊ฐ๋ํฉ๋๋ค.
    - ์ผ์ผ ๋งค๋์ ๋ ์ผํ์ฑ์ผ๋ก ํ๋์ ๋งค์น๋ฅผ ๊ด๋ฆฌ, ๊ฐ๋ํฉ๋๋ค.

### Chatting
- ๋ชจ์ง์ด ๋ง๊ฐ์ด ๋ ์ดํ ์ฐธ์ฌ์ธ์๊ณผ ๋งค๋์ ๊ฐ์ ์ฑํ์ ํ  ์ ์์ต๋๋ค.
- ์ฑํ์ ํตํด ๋งค์น์ ๊ด๋ จ๋ ์ปค๋ฎค๋์ผ์ด์์ ํ  ์ ์์ต๋๋ค.

## ๐ Trouble Shooting

## ๐ API ๋ช์ธ(์์ฑ ์์ )