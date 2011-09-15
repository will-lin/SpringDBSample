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

package test.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

/**
 * 
 * @author Will Lin
 * @since 2011/06/02
 */
public class AccountDAO implements IAccountDAO {
	
    private final SimpleJdbcTemplate template;

    private final SimpleJdbcInsert insertContact;

    private final RowMapper<Account> rowMapper = new ParameterizedRowMapper<Account>() {

        public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
        	Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setAccountId(rs.getString("account_id"));
            account.setAccountName(rs.getString("account_name"));
            account.setPw(rs.getString("pw"));
            account.setEmail(rs.getString("email"));
            return account;
        }
    };

    public AccountDAO(DataSource dataSource) {
        this.template = new SimpleJdbcTemplate(dataSource);
        this.insertContact = new SimpleJdbcInsert(dataSource).withTableName("account").usingGeneratedKeyColumns("id");
    }

    public List<Account> findAll() {
        return this.template.query("SELECT * FROM account ORDER BY account_id", this.rowMapper);
    }

    public List<Account> findByName(String name) {
    	String key = "%" + name.toUpperCase() + "%";
        return this.template.query("SELECT * FROM account WHERE UPPER( account_id ) LIKE ? OR UPPER( account_name ) LIKE ? ORDER BY account_id",
            this.rowMapper, key, key);
    }

    public Account findById(int id) {
        return this.template.queryForObject("SELECT * FROM account WHERE id=?", this.rowMapper, id);
    }
    
    public List<Account> authByAccount( String account_id, String pw ) {
    	List<Account> l = this.template.query("SELECT * FROM account WHERE account_id=? AND pw=?", this.rowMapper, account_id, pw);
    	return l;
    }

    public Account create(Account account) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("account_id", account.getAccountId());
        parameters.put("account_name", account.getAccountName());
        parameters.put("pw", account.getPw());
        parameters.put("email", account.getEmail());
        Number id = this.insertContact.executeAndReturnKey(parameters);
        account.setId(id.intValue());
        return account;
    }

    public boolean update(Account account) {
        int count = this.template.update(
            "UPDATE account SET account_name=?, pw=?, email=? WHERE account_id=?", account.getAccountName(),
            account.getPw(), account.getEmail(), account.getAccountId());
        return count == 1;
    }

    public boolean remove(Account account) {
    	if( account != null && ! account.getAccountId().toLowerCase().equals("admin") ) {	// reject remove admin account (2011/08/15)
	        int count = this.template.update("DELETE FROM account WHERE account_id=?", account.getAccountId());
	        return count == 1;
    	}
    	return false;
    }

}
