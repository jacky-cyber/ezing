actor-server/actor-activation/src/main/scala/im/actor/server/activation/internal/InternalCodeActivation.scala 

actor-server/actor-core/src/main/scala/im/actor/server/office/PushTexts.scala 

actor-server/actor-rpc-api/src/main/scala/im/actor/server/api/rpc/service/profile/ProfileServiceImpl.scala 

actor-server/actor-core/src/main/scala/im/actor/server/user/UserCommandHandlers.scala

actor-server/actor-core/src/main/scala/sql/migration/V20160128142000__AkkaPersistence.scala 

sbt rpm:packageBin


please add println(persistenceId) before V20160128142000__AkkaPersistence.scala:180

execute DELETE FROM journal WHERE persistence_id like 'SeqUpdatesManager%'
executed DELETE FROM journal WHERE persistence_id LIKE '/user/sharding%'
delete '/sharding%'