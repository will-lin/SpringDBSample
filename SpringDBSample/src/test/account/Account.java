/*
 * Copyright 2002-2009 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/*
 * ******************************************************************
 
	CREATE TABLE account
	(
	  id serial NOT NULL,
	  account_id text,
	  account_name text,
	  pw text,
	  email text,
	  PRIMARY KEY (id)
	)
	WITH (
	  OIDS=FALSE
	);

 * ******************************************************************

 */

package test.account;

import java.io.Serializable;

/**
 * 
 * @author Will Lin
 * @since 2011/06/02
 */
public class Account implements Serializable {

    static final long serialVersionUID = 103844514947365244L;

    private int id;
    
    private String accountId;
    
    private String accountName;
    
    private String pw;
    
    private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}
    


}
