class ChangeDataOfBirthDataType < ActiveRecord::Migration
  def change
  	change_column :users, :date_of_birth, :string
  end
end
