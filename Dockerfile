FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD urubu100
COPY ./bd /docker-entrypoint-initdb.d
EXPOSE 3306