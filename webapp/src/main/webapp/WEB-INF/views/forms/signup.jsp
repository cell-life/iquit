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
		<td>What year were you born?</td>
		<td><input type="text" name="year"></td>
	</tr>
	<tr>
		<td>When would you like to quit?</td>
		<td>
			<select name="quit_date">
			  <option value="3">in 3 days</option>
			  <option value="7">in 7 days</option>
			  <option value="10">in 10 days</option>
			  <option value="14">in 14 days</option>
			  <option value="24">in 24 days</option>
			  <option value="30">in 30 days</option>
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
</table>
<button id="submit" type="submit">Submit</button>
</form>