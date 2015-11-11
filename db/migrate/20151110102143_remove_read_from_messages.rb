class RemoveReadFromMessages < ActiveRecord::Migration
  def change
    remove_column :messages, :read, :boolean
  end
end
