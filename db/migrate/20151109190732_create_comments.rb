class CreateComments < ActiveRecord::Migration
  def change
    create_table :comments do |t|
      t.references :owner, index: true
      t.references :post, index: true
      t.string :text

      t.timestamps
    end
  end
end
