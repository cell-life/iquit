<h1>Sign up for iQuit</h1>
<p>Fill in the following details and click the Sign up button to register for the free iQuit SMS service</p>
<form action="../service/iquit-form" method="post">
<table>
	<tr>
		<td>Are you male or female?</td>
		<td>
			<input type="radio" name="gender" value="female">Female
			<br>
			<input type="radio" name="gender" value="male">Male
		</td>
	</tr>
	<tr>
		<td>Are you pregnant or want to get pregnant?</td>
		<td>
			<input type="radio" name="pregnant" value="yes">Yes
			<br>
			<input type="radio" name="pregnant" value="no">No
		</td>
	</tr>
	<tr>
		<td>Your cellphone number</td>
		<td><input type="text" name="msisdn"></td>
	</tr>
	<tr>
		<td>What year were you born?</td>
		<td><input type="text" name="year"></td>
	</tr>
	<tr>
		<td>Where do you live?</td>
		<td>
			<select name="province">
			  <option value="ec">The Eastern Cape</option>
			  <option value="fs">The Free State</option>
			  <option value="gau">Gauteng</option>
			  <option value="kzn">KwaZulu-Natal</option>
			  <option value="lim">Limpopo</option>
			  <option value="nc">The Northern Cape</option>
			  <option value="nw">North West</option>
			  <option value="wc">The Western Cape</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>When would you like to quit?</td>
		<td>
			<select name="quit_date">
			  <option value="in3days">in 3 days</option>
			  <option value="in7days">in 7 days</option>
			  <option value="in10days">in 10 days</option>
			  <option value="in14days">in 14 days</option>
			  <option value="in24days">in 24 days</option>
			  <option value="in30days">in 30 days</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>How many cigarettes do you smoke?</td>
		<td>
			<select name="cigarettes">
			  <option value="less_than_one_pack_a_day">Less than one pack a day</option>
			  <option value="one_pack_a_day">One pack a day</option>
			  <option value="more_than_one_pack_a_day">More than one pack a day</option>
			  <option value="one_pack_a_week">One pack a week</option>
			  <option value="less_than_one_pack_a_month">Less than one pack a month</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>Do you drink at least two drinks at the same time at least once a week?</td>
		<td>
			<input type="radio" name="drinks" value="yes">Yes
			<br>
			<input type="radio" name="drinks" value="no">No
		</td>
	</tr>
	<tr>
		<td>How did you hear about iQuit?</td>
		<td><input type="text" name="reference"></td>
	</tr>
</table>
<button id="submit" type="submit">Submit</button>
</form>