package brainwiz.gobrainwiz.api

import com.apshutters.salesperson.model.BaseRequest

class LoginRequest : BaseRequest() {
    var email: String? = null
    var password: String? = null

}