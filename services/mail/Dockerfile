FROM tomee:8-jre-7.0.3-plus
LABEL MAINTAINER = "Sébastien Mosser mosser@i3s.unice.fr)"

WORKDIR /usr/local/tomee/

COPY ./target/mail-service-rpc.war ./webapps/.
#COPY --from=builder /services/mail/target/mail-service-rpc.war ./webapps/.

HEALTHCHECK --interval=5s CMD curl --fail http://localhost:8080/ || exit 1

EXPOSE 8080
