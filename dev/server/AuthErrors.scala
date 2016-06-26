package im.actor.server.api.rpc.service.auth

import im.actor.api.rpc.{ CommonRpcErrors, RpcError }
import im.actor.server.acl.ACLUtils
import im.actor.server.activation.common.{ BadRequest, SendFailure, CodeFailure }

object AuthErrors {
  val AuthSessionNotFound = RpcError(404, "AUTH_SESSION_NOT_FOUND", "授权会话未找到.", false, None)
  val CurrentSessionTermination = RpcError(400, "CURRENT_SESSION_TERMINATION", "你已经尝试关闭当前授权会话.", false, None)
  val InvalidKey = RpcError(400, "INVALID_KEY", "无效key.", false, None)
  val PhoneNumberInvalid = RpcError(400, "PHONE_NUMBER_INVALID", "无效电话号码.", false, None)
  val PhoneNumberUnoccupied = RpcError(400, "PHONE_NUMBER_UNOCCUPIED", "", false, None)
  val PhoneCodeEmpty = RpcError(400, "PHONE_CODE_EMPTY", "验证码不能为空.", false, None)
  val PhoneCodeExpired = RpcError(400, "PHONE_CODE_EXPIRED", "验证码过期.", false, None)
  val PhoneCodeInvalid = RpcError(400, "PHONE_CODE_INVALID", "无效验证码.", false, None)
  val EmailUnoccupied = RpcError(400, "EMAIL_UNOCCUPIED", "", false, None)
  val EmailCodeExpired = RpcError(400, "EMAIL_CODE_EXPIRED", "验证码过期.", false, None)
  val EmailCodeInvalid = RpcError(400, "EMAIL_CODE_INVALID", "无效验证码.", false, None)
  val UsernameCodeExpired = RpcError(400, "USERNAME_CODE_EXPIRED", "验证码过期.", false, None)
  val UsernameCodeInvalid = RpcError(400, "USERNAME_CODE_INVALID", "无效验证码.", false, None)
  val UsernameUnoccupied = RpcError(400, "USERNAME_UNOCCUPIED", "", false, None)
  val RedirectUrlInvalid = RpcError(400, "REDIRECT_URL_INVALID", "", false, None)
  val NotValidated = RpcError(400, "NOT_VALIDATED", "", false, None) //todo: proper name
  val FailedToGetOAuth2Token = RpcError(400, "FAILED_GET_OAUTH2_TOKEN", "OAuth服务器错误.", false, None)
  val OAuthUserIdDoesNotMatch = RpcError(400, "WRONG_OAUTH2_USER_ID", "邮件与第一步提供的不一致.", false, None)
  val ActivationServiceError = RpcError(500, "ACTIVATION_SERVICE_ERROR", "请求验证码时出错，请稍候再试.", true, None)
  val InvalidAuthCodeHash = RpcError(400, "CODE_HASH_INVALID", "", false, None)
  val PasswordInvalid = RpcError(400, "PASSWORD_INVALID", s"密码长度必须超过 ${ACLUtils.PasswordMinLength} 并且小于${ACLUtils.PasswordMaxLength}", false, None)
  val UserDeleted = CommonRpcErrors.forbidden("无法登录，您的账号已经被删除，请联系客服")

  def activationFailure(failure: CodeFailure): RpcError = failure match {
    case SendFailure(message) ⇒ RpcError(500, "GATE_ERROR", message, true, None)
    case BadRequest(message)  ⇒ RpcError(400, "CODE_WAIT", message, false, None)
  }
}
